import type Paginated from '@/components/funcionarios/Paginated'
import type { funcionario } from '@/components/funcionarios/funcionarioType'
import ApiService from '../ApiService'
import type { AxiosError } from 'axios'
import toast from '@/plugins/vueToast'
import type { cargo } from '@/components/cargos/cargoType'
import filtrarParametrosIndefinidos from '../utils/filtrarParametrosUndefined'
import router from '@/router'

export default class CargoService extends ApiService {
  constructor(token: string | null) {
    super('', null)
  }

  async listarCargos(): Promise<cargo[]> {
    try {
      const response = await this.apiInstance.get('/cargos/all')
      return response.data
    } catch (error: AxiosError | any) {
      toast.error(error.response.data.mensagem)
      return error
    }
  }

  async listarCargosPaginados(params?: any): Promise<Paginated<cargo>> {
    const searchParams = new URLSearchParams(filtrarParametrosIndefinidos(params)).toString()
    try {
      const response = await this.apiInstance.get(`/cargos?${searchParams}`)
      return response.data
    } catch (error: AxiosError | any) {
      toast.error(error.response.data.mensagem)
      return error
    }
  }

  async buscarCargoPorId(id: string): Promise<cargo> {
    try {
      const response = await this.apiInstance.get('/cargos/' + id)
      return response.data
    } catch (error: AxiosError | any) {
      toast.error(error.response.data.mensagem)
      return error
    }
  }

  async cadastrarCargo(cargo: cargo): Promise<cargo> {
    try {
      const response = await this.apiInstance.post('/cargos', cargo)
      return response.data
    } catch (error: AxiosError | any) {
      toast.error(error.response.data.mensagem)
      return error
    }
  }
}
