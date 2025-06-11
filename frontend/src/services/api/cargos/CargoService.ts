import type Paginated from '@/components/funcionarios/Paginated';
import type { funcionario } from '@/components/funcionarios/funcionarioType'
import ApiService from '../ApiService'
import type { AxiosError } from 'axios'
import toast from '@/plugins/vueToast'
import type { cargo } from '@/components/cargos/cargoType';

export default class CargoService extends ApiService {
  constructor(token: string | null) {
    super('', null)
  }

  async buscarCargoPorId(id: number): Promise<cargo> {
    try {
      const response = await this.apiInstance.get('/cargos/' + id)
      return response.data
    } catch (error: AxiosError | any) {
      toast.error(error.response.data.mensagem)
      return error
    }
  }
}
