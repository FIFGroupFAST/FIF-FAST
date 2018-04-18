/**
 * Copyright (c) 2012 FIF. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of FIF.
 * ("Confidential Information"). <BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * FIF; other than in accordance with the written permission of FIF. <BR>
 * 
 * Created on: Aug 25, 2012
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */

package co.id.fifgroup.core.ui;

import org.zkoss.zul.DefaultTreeNode;

public class SimpleTreeNode<T> extends DefaultTreeNode<T> {
    /**
     * 
     */
    private static final long serialVersionUID = -8085873079938209759L;
     
    // Node Control the default open
    private boolean open = false;
 
    public SimpleTreeNode(T data, SimpleTreeNodeCollection<T> children, boolean open) {
        super(data, children);
        this.setOpen(open);
    }
 
    public SimpleTreeNode(T data, SimpleTreeNodeCollection<T> children) {
        super(data, children);
    }
 
    public SimpleTreeNode(T data) {
        super(data);
    }
 
    public boolean isOpen() {
        return open;
    }
 
    public void setOpen(boolean open) {
        this.open = open;
    }
}