import {
  Scatter,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ComposedChart,
  Legend,
  ZAxis,
} from "recharts";

/**
 * Types
 */
type Point = {
  windSpeed: number;
  activePower: number;
};

export type PowerCurveGraphProps = {
  referenceCurve: Point[];
  scatter: Point[];
};

/**
 *
 * @returns
 */
const PowerCurveGraph = ({ referenceCurve, scatter }: PowerCurveGraphProps) => {
  return (
    <>
      <ComposedChart
        width={850}
        height={400}
        margin={{ top: 20, right: 20, bottom: 20, left: 20 }}
      >
        <CartesianGrid />
        <XAxis
          type="number"
          dataKey="windSpeed"
          name="Wind Speed"
          unit="m/s"
          domain={[0, 20]}
          label={{
            value: `Wind power[m/s]`,
            style: { textAnchor: "middle" },
            position: "bottom",
          }}
        />
        <YAxis
          type="number"
          dataKey="activePower"
          name="Active Power"
          unit="W"
          domain={[-100, 4500]}
          label={{
            value: `Active power[kW]`,
            style: { textAnchor: "middle" },
            angle: -90,
            position: "left",
            offset: 15,
          }}
          ticks={[0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500]}
        />
        <Tooltip />
        <Legend
          layout="horizontal"
          verticalAlign="top"
          align="center"
          wrapperStyle={{ top: 0, left: 25 }}
        />
        <ZAxis range={[10]} />

        <Scatter name="Scatter data" data={scatter} fill="blue" />
        <Line
          name="Reference power curve"
          data={referenceCurve}
          stroke="red"
          dataKey="activePower"
          strokeWidth="2"
        />
      </ComposedChart>
    </>
  );
};

export default PowerCurveGraph;
