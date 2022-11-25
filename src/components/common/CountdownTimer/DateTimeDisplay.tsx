import React from 'react';

export interface IDateTimeDisplayProps {
  value: number;
  type: string;
  isDanger: boolean;
}

const DateTimeDisplay = ({ value, type, isDanger }: IDateTimeDisplayProps) => {
  return (
    <div className={isDanger ? 'countdown danger' : 'countdown'}>
      <p>{value}</p>
      <span>{type}</span>
    </div>
  );
};

export default DateTimeDisplay;
