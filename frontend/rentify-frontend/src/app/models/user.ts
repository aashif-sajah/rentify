export interface User {
  userId: number;
  userEmail: string;
  userFirstName: string;
  userLastName: string;
  roles: Role[];
}

interface Role {
  role: string;
  roleDescription: string;
}
