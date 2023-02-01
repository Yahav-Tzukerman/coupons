import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { ActionType } from "../../../redux/action-type";


export default function Logout() {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const deafaultDecodedToken = {
        sub: '',
        roles: '',
        userId: 0,
        exp: 0
    }

    useEffect(() => {
        localStorage.clear();
        dispatch({ type: ActionType.OnLogout, payload: { deafaultDecodedToken } });
        navigate('/');
        window.location.reload();
    }, []);

    return (
        <div>
        </div>
    );
}
