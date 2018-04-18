package co.id.fifgroup.ssoa.dto;

import co.id.fifgroup.ssoa.domain.Parameter;

import java.util.List;


public class ParameterDTO extends Parameter{
	
	private static final long serialVersionUID = 8153696528893553702L;
	
	private List<ParameterDetailDTO> parameterDetailDto;
	private String userName;
	
	public List<ParameterDetailDTO> getParameterDetailDto() {
		return parameterDetailDto;
	}
	public void setParameterDetailDto(List<ParameterDetailDTO> parameterDetailDto) {
		this.parameterDetailDto = parameterDetailDto;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
