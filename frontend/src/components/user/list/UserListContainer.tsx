import { useEffect, useState } from "react";
import UserList from "./UserList";
import useFetchData from "@src/hooks/useFetchData";
import { JsonApi, jsonOrgConfig } from "@src/jsonApiOrg/JsonApiOrg";
import { UserOfRequest } from "../detail/UserDetail";
import { config, getRestApiServerUrl } from "@config/config";

/**
 * UserListContainer Component
 *
 * This component encapsulates the business logic for the UserList component.
 *
 * @author Karden
 * @created 2024-07-19
 */
const UserListContainer = () => {
  const fetchData = useFetchData();
  const [users, setUsers] = useState<Array<JsonApi<UserOfRequest>>>([] as Array<JsonApi<UserOfRequest>>);

  useEffect(() => {
    const fetchDataAsync = async () => {
      const data = await fetchData<Array<JsonApi<UserOfRequest>>>(getRestApiServerUrl(`/users`), {
        mode: "cors",
        method: "GET",
        credentials: "include",
        headers: {
          Accept: jsonOrgConfig.ACCEPT,
          "Content-Type": jsonOrgConfig.CONTENT_TYPE,
        },
      });
      setUsers(data);
    };

    try {
      fetchDataAsync();
    } catch (e) {}
  }, []);

  return <UserList users={users}></UserList>;
};

export default UserListContainer;
