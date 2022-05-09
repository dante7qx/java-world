package com.epolleo.bp.util;

import com.alibaba.citrus.service.pull.ToolFactory;
import com.epolleo.bp.pub.LoginUser;

public class LoginUserTool implements ToolFactory {
    public boolean isSingleton() {
        return false;
    }

    public Object createTool() throws Exception {
        return LoginUser.getCurrentUser();
    }
}
