export default function filtrarParametrosIndefinidos<T extends Record<string, any>>(params: T): Partial<T> {
    return Object.fromEntries(
      Object.entries(params).filter(([_, value]) => value !== undefined)
    ) as Partial<T>;
}