module.exports = {
  css: {
    extract: {
      ignoreOrder: true
    },
  },
  "transpileDependencies": [
    "vuetify"
  ],
  "devServer": {
    port: 3000,
    proxy: {
      "^/rest": {
        target: 'http://localhost:8080'
      },
      "^/cert": {
        target: 'http://localhost:8080'
      }
    }
  }
}