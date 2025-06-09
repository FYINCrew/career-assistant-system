import type Paginated from '@/components/funcionarios/Paginated';
import type { funcionario } from '@/components/funcionarios/funcionarioType'
import ApiService from '../ApiService'
import type { AxiosError } from 'axios'
import toast from '@/plugins/vueToast'

export default class FuncionarioService extends ApiService {
  constructor(token: string | null) {
    super('', null)
  }

  async listarFuncionarios(params?: any): Promise<Paginated<funcionario>> {
  const searchParams = new URLSearchParams(params).toString();
    try {
      const response = await this.apiInstance.get(`/funcionarios?${searchParams}`)
      return response.data
    } catch (error: AxiosError | any) {
      toast.error(error.response.data.mensagem)
      return error
    }
  }
}
