export default interface Paginated<T> {
    content: T[];
    page: number;
    totalElements: number;
    totalPages: number;
    size:number;
}