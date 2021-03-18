import Axios from "axios"
import router from "../router"

const RestUtil = Axios.create({
    withCredentials: true
});


RestUtil.interceptors.response.use((response) => {
    return response;
  },
  (error) => {
    let statusCode = error.response.status
    if( statusCode === 401 || statusCode === 403 ) {
      router.push('/login');
    }
    return Promise.reject(error);
  }
);




export default RestUtil;