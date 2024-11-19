import { forwardRef, RefObject } from "react";
import { CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis } from "recharts";
import styled from "styled-components";
import { format } from "date-fns";
import { arePropsEmpty } from "@src/utils/props";
import React from "react";

/**
 * Style
 */
const Title = styled.div`
  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
`;

/**
 * Types
 */
type Point = {
  timestamp: string;
  rotorSpeed: number;
  activePower: number;
  windSpeed: number;
};

export type DailyRWPGraphProps = {
  timeChart: Point[];
  title: string;
  ratedPower: number;
  ref: RefObject<HTMLDivElement>;
};

const DailyRWPGraph = React.memo(
  forwardRef<HTMLDivElement, DailyRWPGraphProps>(({ title, timeChart, ratedPower }, ref) => {
    if (arePropsEmpty(timeChart)) return null;
    timeChart = timeChart.map((item) => ({
      ...item,
      timestamp: format(new Date(item.timestamp), "HH:mm"),
    }));

    const createYTicks = (ratedPower: number) => {
      let newArr = [];
      for (let i = 1; i <= 9; i++) {
        let tick = (ratedPower * 1.2) / 8;
        tick = tick - (tick % 100);
        newArr.push(tick * i);
      }
      console.log(newArr);

      return newArr;
    };
    return (
      <>
        <Title>{title}</Title>
        <div ref={ref}>
          <LineChart
            width={900}
            height={450}
            data={timeChart}
            margin={{
              top: 5,
              right: 30,
              left: 20,
              bottom: 5,
            }}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis
              dataKey="timestamp"
              ticks={[
                "00:00",
                "02:00",
                "04:00",
                "06:00",
                "08:00",
                "10:00",
                "12:00",
                "14:00",
                "16:00",
                "18:00",
                "20:00",
                "22:00",
              ]}
              label={{
                value: `Time`,
                style: { textAnchor: "middle" },
                position: "bottom",
                offset: -7,
              }}
            />
            <YAxis
              domain={[0, ratedPower * 1.05]}
              type="number"
              yAxisId="1"
              tickFormatter={(data) => (data < 0 ? 0 : data)}
              ticks={createYTicks(ratedPower)}
              label={{
                value: `Active power[kW]`,
                style: { textAnchor: "middle" },
                angle: -90,
                position: "left",
                offset: 15,
              }}
            />
            <YAxis
              orientation="right"
              allowDataOverflow
              domain={[0, 30]}
              type="number"
              yAxisId="2"
              ticks={[5, 10, 15, 20, 25, 30]}
              label={{
                value: `Wind speed[m/s] / Rotor speed[rpm]`,
                style: { textAnchor: "middle" },
                angle: 90,
                position: "right",
                offset: 0,
              }}
            />

            <Tooltip />
            <Legend layout="horizontal" verticalAlign="top" align="center" wrapperStyle={{ top: 0, left: 25 }} />
            <Line
              strokeWidth="2"
              type="monotone"
              dataKey="rotorSpeed"
              name="Rotor speed"
              stroke="blue"
              activeDot={{ r: 8 }}
              yAxisId="2"
              dot={false}
            />
            <Line
              strokeWidth="2"
              type="monotone"
              dataKey="activePower"
              name="Active power"
              stroke="green"
              activeDot={{ r: 8 }}
              yAxisId="1"
              dot={false}
            />
            <Line
              strokeWidth="2"
              type="monotone"
              dataKey="windSpeed"
              name="Wind speed"
              stroke="red"
              activeDot={{ r: 8 }}
              yAxisId="2"
              dot={false}
            />
          </LineChart>
        </div>
      </>
    );
  }),
);

export default DailyRWPGraph;
