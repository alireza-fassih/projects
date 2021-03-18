<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-alert v-model="alert.show" :type="alert.type">{{alert.message}}</v-alert>
        <v-card class="elevation-12">
          <v-toolbar color="blue-grey" dark>
            <v-spacer></v-spacer>

            <v-toolbar-title>Login</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form class="rtl">
              <v-text-field
                v-model="data.username"
                prepend-icon="person"
                name="login"
                label="Username"
                type="text"
              ></v-text-field>
              <v-text-field
                v-model="data.password"
                id="password"
                prepend-icon="lock"
                name="password"
                label="Password"
                type="password"
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn @click.stop="login" :loading="onRequest">Login</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import RestUtil from "../lib/RestUtil";

export default {
  data: () => ({
    onRequest: false,
    alert: {
      show: false,
      message: null,
      type: null
    },
    data: {
      username: null,
      password: null
    }
  }),

  methods: {
    login: function() {
      this.onRequest = true;
      RestUtil.post("/rest/auth/login", this.data)
        .then(resp => {
          this.alert.message = resp.data.message;
          this.alert.show = true;
          this.alert.type = "info";
          this.$router.push("/admin/certs");
        })
        .catch(error => {
          this.onRequest = false;
          this.alert.message = error.response.data.message;
          this.alert.show = true;
          this.alert.type = "warning";
          setTimeout(
            function() {
              this.alert.show = false;
            }.bind(this),
            5000
          );
        });
    }
  }
};
</script>

<style>
i {
  margin-left: 10px;
}
</style>