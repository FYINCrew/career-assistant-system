import FuncionarioService from "@/services/api/funcionarios/FuncionarioService";

export default function useService(){
    /*
    
    Quando usar token para autorizar as requisições, retirar os comentários

    const authStore = useAuthStore();
    const storeToken = authStore.getToken ? authStore.getToken : ''; 
    */

    return{
        funcionarioService: new FuncionarioService(null),
    }
}