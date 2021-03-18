<template>
  <v-app id="inspire">
    <MainNavigation ref="navigation" />

    <v-app-bar color="blue-grey" dark fixed app clipped-left>
      <v-app-bar-nav-icon @click.stop="toggleNavigation"></v-app-bar-nav-icon>

      <v-toolbar-title>Toolbar</v-toolbar-title>
      <v-spacer></v-spacer>

      <v-btn icon @click="logout">
        <v-icon>mdi-logout</v-icon>
      </v-btn>
    </v-app-bar>

    <v-content class="pa-0">
      <v-container>
        <router-view></router-view>
      </v-container>
    </v-content>

    <v-footer color="blue-grey" class="white--text" app>
      <span></span>
      <v-spacer></v-spacer>
      <span></span>
    </v-footer>
  </v-app>
</template>

<script>
import RestUtil from "../lib/RestUtil";

import MainNavigation from "./MainNavigation";

export default {
  components: {
    MainNavigation
  },
  data: () => ({}),
  beforeRouteEnter: function(to, from, next) {
    RestUtil.get("/rest/auth/info").then(() => {
      next();
    });
  },
  methods: {
    toggleNavigation: function() {
      this.$refs.navigation.toggleNav();
    },
    logout: function() {
      RestUtil.get("/rest/auth/logout").then(() => {
        this.$router.push("/login")
      })
    }
  },
  props: {
    source: String
  }
};
</script>

<style>

</style>