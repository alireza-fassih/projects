<template>
  <v-row align="start" justify="start">
    <v-col cols="12">
      <v-data-table :headers="headers" :items="data" disable-sort class="elevation-1">
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
                      <v-text-field v-model="editedItem.code" label="Code"></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="12">
                      <v-file-input
                        accept="image/*"
                        label="Certificate"
                        v-model="image"
                        @change="uploadFile"
                      ></v-file-input>
                    </v-col>
                    <v-col cols="12" sm="12">
                      <v-progress-linear indeterminate :active="onUpload"></v-progress-linear>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn  text @click="close">Close</v-btn>
                <v-btn  text @click="save" :disabled="onUpload">Save</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

          <v-dialog v-model="showCertDialog" max-width="500px">
            <v-card>
              <v-card-title>
                <span class="headline">View certificate</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="12">
                      <v-img :src="showCertLink"></v-img>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text @click="close">Close</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </template>

        <template v-slot:item.actions="{item}">
          <v-icon small @click="showCert(item)">mdi-eye</v-icon>
          <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
        </template>
      </v-data-table>
    </v-col>
  </v-row>
</template>

<script>
import RestUtil from "../lib/RestUtil";
import { v4 as uuidv4 } from "uuid";

export default {
  data: () => ({
    headers: [
      {
        text: "Code",
        value: "code"
      },
      {
        text: "Actions",
        value: "actions"
      }
    ],
    data: [],
    selectedItems: [],
    editedItem: {
      code: null,
      imageId: null
    },
    dialog: false,
    formTitle: "Create new certificate",
    onUpload: false,
    image: null,
    showCertDialog: false,
    showCertLink: null
  }),
  mounted: function() {
    this.reloadUserList();
  },
  methods: {
    deleteItem: function(item) {
      RestUtil.delete("/rest/certs/" + item.id).then(resp => {
        console.log("resp ", resp);
        this.reloadUserList();
      });
    },
    save: function() {
      RestUtil.post("/rest/certs", this.editedItem).then(() => {
        this.clearDialog();
        this.reloadUserList();
      });
    },
    close: function() {
      this.clearDialog();
    },
    reloadUserList: function() {
      RestUtil.get("/rest/certs/search").then(resp => {
        this.data = resp.data;
      });
    },
    clearDialog: function() {
      this.dialog = false
      this.showCertLink = null
      this.showCertDialog = false
      this.image = null
      this.editedItem = {
        code: null,
        imageId: null
      }
    },
    showCert: function(item) {
      this.showCertDialog = true;
      this.showCertLink = "/rest/certs/loadCert/" + item.id;
    },
    uploadFile: function(file) {
      if (!file) {
        this.editedItem.imageId = null;
        return;
      }
      this.editedItem.imageId = uuidv4();
      let formData = new FormData();
      formData.append("file", file);
      this.onUpload = true;
      RestUtil.post("/rest/upload/" + this.editedItem.imageId, formData, {
        headers: {
          "Content-Type": "multipart/form-data"
        },
        onUploadProgress: function(progressEvent) {
          let percentCompleted = Math.round(
            (progressEvent.loaded * 100) / progressEvent.total
          );
          this.uploadProgress = percentCompleted;
        }
      }).then(() => (this.onUpload = false));
    }
  }
};
</script>

<style>
</style>