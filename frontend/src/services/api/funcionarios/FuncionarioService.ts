import type Paginated from '@/components/funcionarios/Paginated';
import type { funcionario } from '@/components/funcionarios/dataFuncionario'
import ApiService from '../ApiService'
import type { AxiosError } from 'axios'
import toast from '@/plugins/vueToast'

export default class FuncionarioService extends ApiService {
  constructor(token: string | null) {
    super('', null)
  }

  async listarFuncionarios(): Promise<Paginated<funcionario>> {
    try {
      const response = await this.apiInstance.get('/funcionarios')
      return response.data
    } catch (error: AxiosError | any) {
      toast.error(error.response.data.mensagem)
      return error
    }
  }
}
