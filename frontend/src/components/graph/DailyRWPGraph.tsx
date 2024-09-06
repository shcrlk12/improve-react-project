import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

/**
 * Types
 */
type Point = {
  time: string;
  rotorSpeed: number;
  activePower: number;
  windSpeed: number;
};

export type DailyRWPGraphProps = {
  dailyRWPGraphProps: Point[];
};

const DailyRWPGraph = ({ dailyRWPGraphProps }: DailyRWPGraphProps) => {
  return (
    <LineChart
      width={900}
      height={450}
      data={dailyRWPGraphProps}
      margin={{
        top: 5,
        right: 30,
        left: 20,
        bottom: 5,
      }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis
        dataKey="time"
        ticks={[
          "0:00",
          "2:00",
          "4:00",
          "6:00",
          "8:00",
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
        domain={[0, 4500]}
        type="number"
        yAxisId="1"
        tickFormatter={(data) => (data < 0 ? 0 : data)}
        ticks={[0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500]}
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
        label={{
          value: `Wind speed[m/s] / Rotor speed[rpm]`,
          style: { textAnchor: "middle" },
          angle: 90,
          position: "right",
          offset: 0,
        }}
      />

      <Tooltip />
      <Legend
        layout="horizontal"
        verticalAlign="top"
        align="center"
        wrapperStyle={{ top: 0, left: 25 }}
      />
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
  );
};

export default DailyRWPGraph;
