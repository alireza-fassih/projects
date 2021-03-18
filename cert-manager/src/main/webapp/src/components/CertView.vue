<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-snackbar v-model="snackbar">
          Link hasbeen copy to your clipboard
          <v-btn color="pink" text @click="snackbar = false">Close</v-btn>
        </v-snackbar>

        <v-card :hidden="invalidCertCode">
          <v-img
            v-on:error="onImageLoadError"
            :src="loadedCertUrl"
            class="white--text align-end"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          >
            <v-card-title v-text="$route.params.code"></v-card-title>
          </v-img>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn icon v-clipboard:copy="url" @click="snackbar = true">
              <v-icon>mdi-share-variant</v-icon>
            </v-btn>

            <v-btn icon @click="gotoHome">
              <v-icon>mdi-home</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>

        <v-card :hidden="!invalidCertCode">
          <v-card-title>The code you entered was not found.</v-card-title>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn icon @click="gotoHome">
              <v-icon>mdi-home</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    loadedCertUrl: "",
    url: "",
    snackbar: false,
    invalidCertCode: false
  }),
  mounted: function() {
    this.loadedCertUrl = "/cert/loadCert/" + this.$route.params.code;
    this.url = window.location.href;
  },
  methods: {
    onImageLoadError: function() {
      this.invalidCertCode = true;
    },
    gotoHome: function() {
      this.$router.push("/");
    }
  }
};
</script>


<style>

</style>