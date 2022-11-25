import React from 'react';
import { useCountdown } from '../../../hooks/useCountDown/useCountdown';
import ShowCounter from './ShowCounter';
import './countdown.css';

export interface ICountDownTimerProps {
    targetDate: Date;
}

const CountdownTimer = ({ targetDate }: ICountDownTimerProps) => {
    const [days, hours, minutes, seconds] = useCountdown(targetDate);

    return (
        <ShowCounter
            days={days}
            hours={hours}
            minutes={minutes}
            seconds={seconds}
        />
    );
};


export default CountdownTimer;