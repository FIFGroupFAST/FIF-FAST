package co.id.fifgroup.workstructure.service;

import org.zkoss.zul.DefaultTreeNode;

import co.id.fifgroup.workstructure.domain.OrganizationNode;

import co.id.fifgroup.core.ui.SimpleTreeNode;


public interface TreeModelService {
	void remove(SimpleTreeNode<OrganizationNode> parent, int idxFrom, int idxTo);
	void remove(DefaultTreeNode<OrganizationNode> node, SimpleTreeNode<OrganizationNode> target);
	void insert(SimpleTreeNode<OrganizationNode> parent, int idxFrom, int idxTo, SimpleTreeNode<OrganizationNode>[] newNodes);
	void add(SimpleTreeNode<OrganizationNode> parent, SimpleTreeNode<OrganizationNode>[] newNodes);
}
