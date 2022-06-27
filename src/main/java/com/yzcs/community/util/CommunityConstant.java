package com.yzcs.community.util;

public interface CommunityConstant {
    // 邮箱激活成功
    int ACTIVATION_SUCCESS = 0;
    // 邮箱激活成功
    int ACTIVATION_REPEAT = 1;
    // 邮箱激活成功
    int ACTIVATION_FAILURE = 2;

    // 默认状态的激活凭证的超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;
    // 记住状态的激活凭证超时时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;
}
