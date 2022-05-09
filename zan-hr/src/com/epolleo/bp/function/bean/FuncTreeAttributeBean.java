/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 */
package com.epolleo.bp.function.bean;

import java.io.Serializable;

/**
 * 
 * FunctionTreeBean
 * Date:2012-07-16,10:30:02 +0800
 *
 * @author nj.sun
 * @version 1.0
 */
public class FuncTreeAttributeBean implements Serializable {

    private static final long serialVersionUID = -9072407964710840404L;
    private boolean state;

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}