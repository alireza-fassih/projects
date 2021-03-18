import Vue from 'vue';
import Vuetify from 'vuetify/lib';

import 'material-design-icons-iconfont/dist/material-design-icons.css'
import 'vazir-font/dist/font-face.css'
import '@mdi/font/css/materialdesignicons.css'
import '../style/styles.sass'



// import fa from 'vuetify/es5/locale/fa'

Vue.use(Vuetify);


export default new Vuetify({
    iconfont: 'md',
    // rtl: 'true',
    // lang: {
        // locales: { fa },
        // current: 'fa',
    // }
});
