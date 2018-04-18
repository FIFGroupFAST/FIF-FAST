package co.id.fifgroup.workstructure.ui.tree;

import org.zkoss.zul.DefaultTreeNode;

import co.id.fifgroup.core.ui.SimpleTreeNodeCollection;

public class Node<T> extends DefaultTreeNode<T> {
	
	private static final long serialVersionUID = 1L;
	private boolean open = false;
	
	public Node(T data) {
		super(data);
	}

	public Node(T data, SimpleTreeNodeCollection<T> children) {
		super(data, children);
	}
	
	public Node(T data, SimpleTreeNodeCollection<T> children, boolean open) {
		super(data, children);
		setOpen(open);
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
}
