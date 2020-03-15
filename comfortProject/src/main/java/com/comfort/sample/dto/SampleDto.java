package com.comfort.sample.dto;

import lombok.Data;

@Data
public class SampleDto {

	private String menuId;
	private String menuName;
	private String menuPrice;
	private String menuKind;
	private String menuKindName;
	private String menuState;
	private String menuStateName;
	private String menuPriority;
	private String menuLastUpdate;
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuPrice() {
		return menuPrice;
	}
	public void setMenuPrice(String menuPrice) {
		this.menuPrice = menuPrice;
	}
	public String getMenuKind() {
		return menuKind;
	}
	public void setMenuKind(String menuKind) {
		this.menuKind = menuKind;
	}
	public String getMenuKindName() {
		return menuKindName;
	}
	public void setMenuKindName(String menuKindName) {
		this.menuKindName = menuKindName;
	}
	public String getMenuState() {
		return menuState;
	}
	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}
	public String getMenuStateName() {
		return menuStateName;
	}
	public void setMenuStateName(String menuStateName) {
		this.menuStateName = menuStateName;
	}
	public String getMenuPriority() {
		return menuPriority;
	}
	public void setMenuPriority(String menuPriority) {
		this.menuPriority = menuPriority;
	}
	public String getMenuLastUpdate() {
		return menuLastUpdate;
	}
	public void setMenuLastUpdate(String menuLastUpdate) {
		this.menuLastUpdate = menuLastUpdate;
	}
	
	
}
