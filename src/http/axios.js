import axios from 'axios'
import config from './config'
import Cookies from 'js-cookie'
import router from '@/router'

export default function $axios(options){
	return new Promise((resolve,reject) => {
		const instance = axios.create({
			baseURL: config.baseUrl,
			headers: config.headers,
			timeout: config.timeout,
			withCredentials: config.withCredentials
		})
		instance.interceptors.request.use(
			config => {
				let token = Cookies.get('token');
				if(token){
					config.headers.token = token;
				}else{
					router.push('/login');
				}
				return config;
			},
			error => {
				return Promise.reject(error);
			}
		)
		instance.interceptors.response.use(
			response => {
				return response.data
			},
			err => {
				if(err.response.status==403){
					sessionStorage.removeItem("user");
					router.push('/login');
				}
				return Promise.reject(err)
			}
		)
		instance(options).then(res => {
			resolve(res);
			return false;
		}).catch(error => {
			reject(error)
		})
	})
}