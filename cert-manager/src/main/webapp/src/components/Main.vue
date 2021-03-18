<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card class="elevation-12">
          <v-img class="white--text align-end" height="300px" :src="image.logo">
            <!-- <v-card-title>مشاهده گواهینامه</v-card-title> -->
          </v-img>

          <v-card-text class="text--primary">
            <v-text-field
              v-model="data.code"
              prepend-icon="mdi-certificate-outline"
              label="Code"
              type="text"
            ></v-text-field>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn @click="loadCert">View</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    data: {
      code: null
    },
    image: {
      logo: require("../assets/logo.png")
    }
  }),
  methods: {
    loadCert: function() {
      if (this.data.code) {
        this.$router.push("/showCert/" + this.data.code);
      }
    }
  }
};
</script>


<style>
</style>