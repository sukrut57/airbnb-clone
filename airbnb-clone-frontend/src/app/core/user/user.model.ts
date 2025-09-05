export interface User {
  email: string
  firstName: string
  lastName: string
  profilePictureUrl: any
  accountEnabled: boolean
  publicId: string
  authorities: Authority[]
}

export interface Authority {
  name: string
}
