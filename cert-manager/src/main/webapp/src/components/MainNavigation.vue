<template>
  <v-navigation-drawer v-model="isOpen" fixed clipped app>
    <v-list dense>
      <v-list-item 
          v-for="item in items"
          :key="item.title"
          @click="changeRoute(item.path)">

        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>


        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>


      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>

import router from '../router'

export default {
  data: () => ({
    isOpen: true,
    items: [
      { title: "Certificate list", icon: "mdi-certificate-outline", path: "certs" },
      { title: "User list", icon: "mdi-account", path: "users" },
    ]
  }),
  methods: {
    toggleNav: function() {
      this.isOpen = !this.isOpen;
    },
    changeRoute: function(path) {
      router.push( '/admin/' + path )
    }
  }
};
</script>