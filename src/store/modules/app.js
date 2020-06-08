export default {
	state: {
		appName: "Mango PlatForm",
		themeColor: "#14889A",
		oldThemeColor: "#14889A",
		collapse: false,
		menuRouteLoaded: false
	},
	getters: {
		collapse(state){
			return state.collapse
		}
	},
	mutations: {
		onCollapse(state){
			state.collapse = !state.collapse
		},
		setThemeColor(state,themeColor){
			state.oldThemeColor = state.themeColor
			state.themeColor = themeColor
		},
		menuRouteLoaded(state, menuRouteLoaded){  // 改变菜单和路由的加载状态
			state.menuRouteLoaded = menuRouteLoaded;
		}
	},
	actions: {
	}
	
	
	
	
	
	
	
}