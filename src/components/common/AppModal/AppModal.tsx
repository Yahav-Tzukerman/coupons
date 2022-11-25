import { ReactNode, useState } from 'react';
import Modal from 'react-bootstrap/Modal'
import './AppModal.css';


const { Header, Body } = Modal

interface IAppModalProps {
    title: string,
    children: ReactNode,
    edit?: boolean,
    button?: string
}

const AppModal = ({ title, children, edit, button }: IAppModalProps) => {


    const [visible, setVisible] = useState<boolean>(false);
    const handleShow = () => setVisible(true);
    const handleHide = () => setVisible(false);

    return (
        <>
            {button ? <input type="button" value="edit" className={button} onClick={handleShow} /> : <input type="button" className="material-symbols-outlined" value={edit ? 'edit' : 'add'} data-tip="delete coupon" data-place="left" onClick={handleShow} />}
            <Modal
                show={visible}
                onHide={handleHide}
                className="modal"
            >
                <Header closeButton>
                    {title}
                </Header>
                <Body>
                    {children}
                </Body>
            </Modal>
        </>
    );
}

export default AppModal;
