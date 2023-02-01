import DateTimeDisplay from "./DateTimeDisplay";

export interface ICounterProps {
    days: number;
    hours: number;
    minutes: number;
    seconds: number;
}

const ShowCounter = ({ days, hours, minutes, seconds }: ICounterProps) => {
    return (
      <div className="show-counter">
        <a
          className="countdown-link"
        >
          <DateTimeDisplay value={days} type={'Days'} isDanger={days <= 3} />
          <p>:</p>
          <DateTimeDisplay value={hours} type={'Hours'} isDanger={days <= 3} />
          <p>:</p>
          <DateTimeDisplay value={minutes} type={'Mins'} isDanger={days <= 3} />
          <p>:</p>
          <DateTimeDisplay value={seconds} type={'Seconds'} isDanger={days <= 3} />
        </a>
      </div>
    );
  };
  
export default ShowCounter;