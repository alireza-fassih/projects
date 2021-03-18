<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card>
          <v-card-title>Resouce not found.</v-card-title>

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
  data: () => ({}),
  methods: {
    gotoHome: function() {
      this.$router.push("/");
    }
  }
};
</script>


<style>
</style>