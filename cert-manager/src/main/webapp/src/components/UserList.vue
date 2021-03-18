<template>
  <v-row align="start" justify="start">
    <v-col cols="12">
      <v-data-table
        :headers="headers"
        :items="data"
        disable-sort
        class="elevation-1"
      >
        <template v-slot:top>
          <v-dialog v-model="dialog" max-width="500px">
            <template v-slot:activator="{ on }">
              <v-btn class="mb-2" v-on="on">Create</v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="12">
                      <v-text-field v-model="editedItem.username" label="Username"></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="12">
                      <v-text-field v-model="editedItem.password" type="password" label="Password"></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text @click="close">Close</v-btn>
                <v-btn text @click="save">Save</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </template>

        <template v-slot:item.actions="{item}">
          <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
        </template>
      </v-data-table>
    </v-col>
  </v-row>
</template>

<script>
import RestUtil from "../lib/RestUtil";


export default {
  data: () => ({
    headers: [
      {
        text: "Username",
        value: "username"
      },
      {
        text: "Actions",
        value: "actions"
      }
    ],
    data: [],
    selectedItems: [],
    editedItem: {
      username: null,
      password: null
    },
    dialog: false,
    formTitle: "Create new user"
  }),
  watch: {
    selectedItems: function(newOne, oldOne) {
      console.log("new ", newOne, " old ", oldOne);
    }
  },
  mounted: function() {
    this.reloadUserList();
  },
  methods: {
    deleteItem: function(item) {
      RestUtil.delete("/rest/user/" + item.id).then(resp => {
        console.log("resp ", resp);
        this.reloadUserList();
      });
    },
    save: function() {
      RestUtil.post("/rest/user", this.editedItem).then(resp => {
        this.clearDialog();
        console.log("resp ", resp);
        this.reloadUserList();
      });
    },
    close: function() {
      this.clearDialog();
    },
    reloadUserList: function() {
      RestUtil.get("/rest/user/search").then(resp => {
        this.data = resp.data;
      });
    },
    clearDialog: function() {
      this.dialog = false;
      this.editedItem = {
        username: null,
        password: null
      };
    }
  }
};
</script>

<style>
</style>