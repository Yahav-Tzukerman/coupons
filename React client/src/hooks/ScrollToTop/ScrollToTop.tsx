import { useLocation } from 'react-router-dom';
import { useEffect } from 'react';

interface ScrollToTopProps {
    children: React.ReactNode
}

const ScrollToTop = ({ children }: ScrollToTopProps) => {

    const { pathname } = useLocation();

    useEffect(() => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    }, [pathname])

    return (
        <>
            {
                children
            }
        </>
    );
};

export default ScrollToTop;