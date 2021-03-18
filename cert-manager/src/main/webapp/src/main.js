import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'

import VueClipboard from 'vue-clipboard2'

import router from './router'

Vue.config.productionTip = false
Vue.use(VueClipboard);


new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
