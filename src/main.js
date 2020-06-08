import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css' 
import api from './http'
import global from '@/utils/global'
import i18n from './i18n'
import store from './store'


Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(api);
Vue.prototype.global = global


new Vue({
  router,
  i18n,
  store,
  render: h => h(App),
}).$mount('#app')
