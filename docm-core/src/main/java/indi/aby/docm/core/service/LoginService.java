package indi.aby.docm.core.service;

import indi.aby.docm.api.ILoginServiceApi;
import indi.aby.docm.api.dto.UserSummaryVO;
import indi.aby.docm.api.dto.UserVO;
import indi.aby.docm.core.dao.UserMapper;
import indi.aby.docm.core.entity.UserEntity;
import indi.rui.common.base.util.JwtUtil;
import indi.rui.common.web.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Slf4j
@Service
public class LoginService extends AbstractService implements ILoginServiceApi {
    @Value("${docm.auth.token.issuer:docm}")
    private String issuer;

    @Value("${docm.auth.token.subject:authentication}")
    private String subject;

    @Value("${docm.auth.token.audience:frontend}")
    private String audience;

    @Value("${docm.auth.token.expireTime:600000}")
    private Long expireTime;

    @Value("${docm.auth.token.priKey}")
    private String priKey;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserSummaryVO login(UserVO vo, HttpServletResponse servletResponse) {
        UserEntity entity = userMapper.findByUsername(vo.getUsername());
        if (entity == null || !entity.getPassword().equals(vo.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        try {
            String token = JwtUtil.build(issuer, subject, audience, expireTime,
                    Collections.singletonMap("id", entity.getId()), priKey);
            log.info("用户登录token：{}", token);
            servletResponse.addHeader("X-AUTH-TOKEN", token);
            return copyProperties(entity, UserSummaryVO.class);
        } catch (Exception e) {
            throw new RuntimeException("登录异常");
        }
    }
}
