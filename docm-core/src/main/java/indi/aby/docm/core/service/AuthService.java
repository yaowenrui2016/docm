package indi.aby.docm.core.service;

import indi.aby.docm.api.IAuthServiceApi;
import indi.aby.docm.api.dto.UserSummaryVO;
import indi.aby.docm.api.dto.UserVO;
import indi.aby.docm.api.util.ErrorCode;
import indi.aby.docm.core.dao.UserMapper;
import indi.aby.docm.core.entity.UserEntity;
import indi.rui.common.base.dto.DefaultStatus;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.util.JwtUtil;
import indi.rui.common.web.exception.BizException;
import indi.rui.common.web.service.AbstractService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Slf4j
@Service
public class AuthService extends AbstractService implements IAuthServiceApi {
    @Value("${docm.auth.token.issuer:docm}")
    private String issuer;

    @Value("${docm.auth.token.subject:authentication}")
    private String subject;

    @Value("${docm.auth.token.audience:frontend}")
    private String audience;

    @Value("${docm.auth.token.expireTime:600000}")
    private Long expireTime;

    @Value("${docm.auth.token.renewRatio:5}")
    private Long renewRatio;

    @Value("${docm.auth.token.priKey}")
    private String priKey;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserSummaryVO login(UserVO vo, HttpServletResponse servletResponse) {
        UserEntity entity = userMapper.findByUsername(vo.getUsername());
        if (entity == null || !entity.getPassword().equals(vo.getPassword())) {
            throw new BizException(ErrorCode.USERNAME_OR_PASSWORD_WRONG);
        }
        couldLogin(entity);     // 检查账号是否满足登录要求
        servletResponse.addHeader("X-AUTH-TOKEN", genToken(entity));
        return copyProperties(entity, UserSummaryVO.class);
    }

    private String genToken(IFieldId fieldId) {
        try {
            return JwtUtil.build(issuer, subject, audience, expireTime,
                    Collections.singletonMap("id", fieldId.getId()), priKey);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BizException(DefaultStatus.EXCEPTION, "生成token异常");
        }
    }

    private void couldLogin(UserEntity entity) {
        if (entity.getFrozen()) {
            throw new BizException(ErrorCode.ACCOUNT_IS_FROZEN);
        }
        if (!entity.getActivate()) {
            throw new BizException(ErrorCode.ACCOUNT_IS_NOT_ACTIVATED);
        }
    }

    @Override
    public UserSummaryVO parse(String token, HttpServletResponse servletResponse) {
        try {
            Claims claims = JwtUtil.parse(token, priKey);
            String id = (String) claims.get("id");
            UserEntity entity = userMapper.findById(IdVO.ofId(id));
            if (entity == null) {
                throw new BizException(DefaultStatus.RECORD_NOT_EXISTS);
            }
            couldLogin(entity); // 检查账号是否满足登录要求
            renewToken(claims, servletResponse);    // token续租
            return copyProperties(entity, UserSummaryVO.class);
        } catch (Exception e) {
            throw new BizException(ErrorCode.LOGIN_TIMEOUT);
        }
    }

    private void renewToken(Claims claims, HttpServletResponse servletResponse) {
        long startTime = claims.getIssuedAt().getTime();
        long endTime = claims.getExpiration().getTime();
        long past = System.currentTimeMillis() - startTime;
        long expireInterval = endTime - startTime;
        long value = expireInterval / renewRatio;
        if (past > value ) {   // 大于1/5的过期时间时续租
            servletResponse.addHeader("X-AUTH-TOKEN", genToken(IdVO.ofId((String) claims.get("id"))));
        }
    }
}
