import axios, { type AxiosInstance } from 'axios';

const config = {
  baseURL: import.meta.env.VITE_API_URL,
  
};

export default (path: string, headers?: any): AxiosInstance =>
  axios.create({
    ...config,
    baseURL: `${config.baseURL}/${path}`,
    headers: {
      common: headers,
    }
  });