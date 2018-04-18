package co.id.fifgroup.workstructure.ui.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.DefaultTreeNode;

import co.id.fifgroup.workstructure.domain.OrganizationNode;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;

import co.id.fifgroup.core.ui.SimpleTreeNodeCollection;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class OrganizationList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private OrganizationHierarchySetupService organizationHierarchySetupServiceImpl = (OrganizationHierarchySetupService) SpringUtil.getBean("organizationHierarchySetupServiceImpl");

	private DefaultTreeNode<OrganizationNode> root;
	
	public OrganizationList(Long hierarchyId, Long rootId, Long versionId) {
		final OrganizationNode orgNode = organizationHierarchySetupServiceImpl.findOrganizationNode(hierarchyId, rootId, versionId, null, false);
		final OrganizationNode rootNode = new OrganizationNode();
		orgNode.setParent(rootNode);
		
		List<OrganizationNode> child = new ArrayList<>();
		child.add(orgNode);
		rootNode.setData(new OrganizationDTO());
		rootNode.setChildren(child);
		root = getTreeModel(rootNode);
	}
	
	private Node<OrganizationNode> getTreeModel(OrganizationNode orgNode) {
		SimpleTreeNodeCollection<OrganizationNode> children = buildNode(orgNode);
		Node<OrganizationNode> treeNode = new Node<OrganizationNode>(new OrganizationNode(), children, true);
    	return treeNode;
	}
	
	private SimpleTreeNodeCollection<OrganizationNode> buildNode(OrganizationNode orgNode){
		SimpleTreeNodeCollection<OrganizationNode> child =  new SimpleTreeNodeCollection<>();
		child.addAll(populateMenu(orgNode));
		return child;
	}
	
	private SimpleTreeNodeCollection<OrganizationNode> populateMenu(OrganizationNode orgNode){
		SimpleTreeNodeCollection<OrganizationNode> ch = new SimpleTreeNodeCollection<>();
    	
		if(orgNode.getChildren().size() > 0) {
			for(OrganizationNode childNode : orgNode.getChildren()) {
				SimpleTreeNodeCollection<OrganizationNode> child =  null;
				if(childNode.getChildren().size() > 0) {
					child = buildNode(childNode);
				}
				Node<OrganizationNode> node = new Node<OrganizationNode>(childNode, child, true);
				ch.add(node);
			}
		} else {
			Node<OrganizationNode> node = new Node<OrganizationNode>(orgNode);
			ch.add(node);
		}
		return ch;
	}

	public DefaultTreeNode<OrganizationNode> getRoot() {
		return root;
	}

}
