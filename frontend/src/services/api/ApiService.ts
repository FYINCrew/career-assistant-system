import type { AxiosInstance } from 'axios';
import axiosApi from './axiosApi';
import toast from '@/plugins/vueToast';

class ApiService {
  protected readonly apiInstance: AxiosInstance;

  protected readonly token: string | null;

  constructor(path: string, token: string | null) {
    this.token = token;

    this.apiInstance = token
      ? axiosApi(path, { Authorization: `Bearer ${token}` })
      : axiosApi(path);
  }

  protected success(mensagem: string) {
    toast.success(mensagem);
  }
}

export default ApiService;