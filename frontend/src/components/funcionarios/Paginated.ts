export default interface Paginated<T> {
    content: T[];
    total: number;
    page: number;
    perPage: number;
}