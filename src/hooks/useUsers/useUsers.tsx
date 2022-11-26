import React, { useEffect, useState } from "react";
import { IUserResponse } from '../../models/IUserResponse';
import UserService from '../../services/users.service';
import usePagination from "../usePagination/usePagination";
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';

function useUsers() {

    const [users, setUsers] = useState<IUserResponse[]>([]);
    const { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements, setPageSize } = usePagination();

    function onDeleteUser(id: number) {
        confirmAlert({
            title: 'Confirm to delete',
            message: 'Are you sure you want to delete this coupon?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => {
                        UserService.deleteUser(id).then((response) => {
                            alert(`coupon ${id} was deleted successfully!`)
                        }).catch((error) => {
                            alert(error.response.data.message);
                        });
                        window.location.reload();
                    }
                },
                {
                    label: 'No',
                }
            ]
        });


    }

    useEffect(() => {
        UserService.getAllUsers(pageNumber, pageSize).then((response) => {
            setUsers(response.data.content);
            setTotalElements(response.data.totalElements);
        });
    }, [pageNumber]);

    return {
        users,
        usersPagination: { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements, setPageSize },
        onDeleteUser
    }
}

export default useUsers;