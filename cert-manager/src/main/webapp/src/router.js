import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

let router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [{
      path: '/login',
      name: 'login',
      component: () => import('./components/Login.vue')
    }, {
      path: '/admin',
      name: 'admin',
      children: [ {
          path: 'users',
          component: () => import('./components/UserList.vue')
        } , {
          path: 'certs',
          component: () => import('./components/CertList.vue')
        }
      ],
      component: () => import('./components/Admin.vue')
    }, {
      name: 'showCert',
      path: '/showCert/:code',
      component: () => import('./components/CertView.vue')
    }, {
      path: '/',
      name: 'main',
      component: () => import('./components/Main.vue')
    },{
      path: "*",
      component: () => import('./components/NotFound.vue')
    }
  ]
});

export default router;