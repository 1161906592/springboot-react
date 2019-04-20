declare namespace Project {
  export interface Route {
    path: string
    name?: string
    redirect?: string
    component?: LoadableExport.LoadableComponent
    children?: Array<Route>
    fullPath?: string
  }
}
