package co.id.fifgroup.workstructure.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeNode;

import co.id.fifgroup.workstructure.constant.HierarchyType;
import co.id.fifgroup.workstructure.domain.OrganizationNode;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.workstructure.dto.OrgHierElementDTO;

public class AdvancedTreeModel extends DefaultTreeModel<OrganizationNode> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(AdvancedTreeModel.class);
	
	private OrganizationSetupService organizationSetupServiceImpl = (OrganizationSetupService) SpringUtil.getBean("organizationSetupServiceImpl");
	
	DefaultTreeNode<OrganizationNode> _root;
	private List<OrgHierElementDTO> elements;
	private String hierType;

	public AdvancedTreeModel(DefaultTreeNode<OrganizationNode> orgNode, List<OrgHierElementDTO> elements) {
		super(orgNode);
		this.elements = elements;
		this._root = orgNode;
	}
	
	public void setHierType(String hierType) {
		this.hierType = hierType;
	}

	public void dragged(DefaultTreeNode<OrganizationNode> target, DefaultTreeNode<OrganizationNode> source, DefaultTreeNode<OrganizationNode> sourceParent) {
		if(hierType.equals(HierarchyType.FUNCTIONAL.toString())) {
			if(validateLevel(target, source)) {
				remove(source); 
				add(target, source, sourceParent);
			} else {
				Messagebox.show(Labels.getLabel("wos.cannotMoveOrganizationBecauseLevelOrganizationOfChildIsHigherThanLevelOrganizationOfParent"));
			}
		} else if(hierType.equals(HierarchyType.STRUCTURAL.toString())) {
			if(validateLevel(target, source)) {
				remove(source); 
				add(target, source, sourceParent);
			} else {
				Messagebox.show(Labels.getLabel("wos.cannotMoveOrganizationBecauseLevelOrganizationOfChildIsHigherThanLevelOrganizationOfParent"));
			}
		}
	}
	
	private boolean validateLevel(DefaultTreeNode<OrganizationNode> target, DefaultTreeNode<OrganizationNode> source) {
		Long newParentId = target.getData().getData().getId();
		Long draggedId = source.getData().getData().getId();
		Integer parentSequence = organizationSetupServiceImpl.findSequenceOfOrganization(newParentId);
		Integer draggedSequence = organizationSetupServiceImpl.findSequenceOfOrganization(draggedId);
		
		if(draggedSequence.compareTo(parentSequence) < 0 || draggedSequence.equals(parentSequence)) {
			return false;
		}
		return true;
	}
	
	public void remove(DefaultTreeNode<OrganizationNode> parent, int idxFrom,
			int idxTo) {
		DefaultTreeNode<OrganizationNode> dtn = parent;
		for(int i=idxTo; i>=idxFrom; i--) {
			try {
				dtn.getChildren().remove(i);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void remove(DefaultTreeNode<OrganizationNode> target) throws IndexOutOfBoundsException {
		int idx = 0;
		DefaultTreeNode<OrganizationNode> parent = null;
		parent = dfSearchParent(_root, target);
		for(idx=0; idx<parent.getChildCount(); idx++) {
			if(parent.getChildAt(idx).equals(target)) {
				break;
			}
		}
		remove(parent, idx, idx);
	}

	public void insert(DefaultTreeNode<OrganizationNode> parent, int idxFrom, int idxTo,
			DefaultTreeNode<OrganizationNode>[] newNodes) {
		DefaultTreeNode<OrganizationNode> dtn = parent;
		for(int i=idxFrom; i<=idxTo; i++) {
			try{
				dtn.getChildren().add(i, newNodes[i-idxFrom]);
			} catch (Exception e) {
				throw new IndexOutOfBoundsException("Out of bound :" + i + "while size :" + dtn.getChildren().size());
			}
		}
	}
	
	public void add(DefaultTreeNode<OrganizationNode> parent, DefaultTreeNode<OrganizationNode> newNode, DefaultTreeNode<OrganizationNode> sourceParent) throws IllegalArgumentException {
		try {
			if(parent.getChildren() == null) {
				parent.add(newNode);
			}
			parent.getChildren().add(newNode);
			if(parent.getData().getData() != null){
				Long newParentId = parent.getData().getData().getId();
				Long draggedNodeId = newNode.getData().getData().getId();
				for(OrgHierElementDTO element : elements) {
					if(element.getOrganizationId().equals(draggedNodeId))
						element.setParentId(newParentId);
				}
			}
		} catch (IllegalArgumentException illegal) {
			add(sourceParent, newNode, null);
			logger.error(illegal.getMessage(), illegal);
			Messagebox.show(illegal.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage());
		}
	}

	private DefaultTreeNode<OrganizationNode> dfSearchParent(DefaultTreeNode<OrganizationNode> node, DefaultTreeNode<OrganizationNode> target) {
		if(node.getChildren() != null && node.getChildren().contains(target)) { 
			return node;
		} else {
			int size = getChildCount(node);
			for(int i=0; i<size; i++) {
				DefaultTreeNode<OrganizationNode> parent = dfSearchParent((DefaultTreeNode<OrganizationNode>)getChild(node, i), target);
				if(parent != null) {
					return parent;
				}
			}
		}
		return null;
	}

	public List<OrgHierElementDTO> getElements() {
		return elements;
	}
}
