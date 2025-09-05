export class UriConstants{

  private static readonly BASE_URL = "http://localhost:8080";
  private static readonly API_VERSION = "/api/v1";

  private static readonly user = "/user";


  static getUserUri(): string{
    return this.BASE_URL + this.API_VERSION + this.user;
  }

}
