import axios from "axios";
import httpConfig from "@/http/config";
import { requestErrorInterceptor, requestInterceptor, responseErrorInterceptor, responseInterceptor } from "@/http/interceptors";

const http = axios.create(httpConfig);

http.interceptors.request.use(requestInterceptor, requestErrorInterceptor);

http.interceptors.response.use(responseInterceptor, responseErrorInterceptor);

export default http;
