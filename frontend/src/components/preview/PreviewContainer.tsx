import Preview from "./Preview";
import { PowerCurveGraphProps } from "@components/graph/PowerCurveGraph";
import { DailyRWPGraphProps } from "@components/graph/DailyRWPGraph";
import { AlarmTableProps } from "./table/AlarmTable";
import EventTextBox from "./EventTextBox";
import { useState } from "react";

const PreviewContainer = () => {
  const powerCurveGraphProps = {} as PowerCurveGraphProps;

  powerCurveGraphProps.referenceCurve = [
    {
      windSpeed: 3,
      activePower: 60.2118,
    },
    {
      windSpeed: 3.5,
      activePower: 141.971,
    },
    {
      windSpeed: 4,
      activePower: 245.051,
    },
    {
      windSpeed: 4.5,
      activePower: 366.097,
    },
    {
      windSpeed: 5,
      activePower: 522.061,
    },
    {
      windSpeed: 5.5,
      activePower: 718.677,
    },
    {
      windSpeed: 6,
      activePower: 958.851,
    },
    {
      windSpeed: 6.5,
      activePower: 1246.71,
    },
    {
      windSpeed: 7,
      activePower: 1590.22,
    },
    {
      windSpeed: 7.5,
      activePower: 1981.7,
    },
    {
      windSpeed: 8,
      activePower: 2432.72,
    },
    {
      windSpeed: 8.5,
      activePower: 2887.12,
    },
    {
      windSpeed: 9,
      activePower: 3355.21,
    },
    {
      windSpeed: 9.5,
      activePower: 3828.27,
    },
    {
      windSpeed: 10,
      activePower: 4300,
    },
    {
      windSpeed: 20,
      activePower: 4300,
    },
  ];

  const realPowerCurveData = `4.846	521.407
  6.07	1,034.504
  5.534	855.117
  4.889	534.043
  4.775	474.218
  4.853	490.475
  6.507	1,356.802
  7.009	1,636.326
  6.428	1,320.37
  5.768	844.941
  5.565	751.3
  3.851	180.16
  3.931	198.671
  5.099	631.97
  6.118	1,067.468
  5.793	916.98
  5.13	596.795
  8.348	2,798.534
  7.779	2,652.901
  6.321	1,386.74
  4.788	491.672
  4.545	382.052
  5.247	551.018
  3.618	171.813
  3.947	229.863
  3.725	206.422
  3.899	176.675
  4.485	393.925
  3.121	32.433
  3.079	0.025
  3.346	103.474
  3.823	188.4
  4.019	243.465
  3.825	207.171
  3.753	180.694
  3.957	263.174
  4.59	403.256
  4.478	404.75
  4.357	354.094
  4.512	354.484
  4.442	353.737
  4.501	378.663
  4.343	321.355
  3.943	244.118
  3.748	188.525
  4.476	372.793
  4.315	386.626
  4.45	367.508
  4.418	392.018
  4.206	343.797
  4.249	340.483
  4.289	373.358
  4.204	305.56
  3.501	162.533
  3.189	78.515
  2.956	5.211
  3.156	48.78
  2.914	25.486
  3.512	138.861
  3.854	235.909
  3.767	209.889
  3.58	149.669
  3.186	58.401
  3.17	3.048
  3.279	99.819
  3.886	198.515
  3.651	135.075
  3.17	26.411
  4.283	317.314
  3.846	222.276
  3.526	140.13
  3.027	36.363
  3.08	57.374
  3.321	134.181
  3.651	233.187
  3.606	218.942
  3.782	222.746
  3.867	280.314
  3.67	229.713
  3.839	241.398
  3.971	314.238
  3.742	205.877
  3.29	91.326
  3.6	199.052
  3.428	155.398
  3.647	234.637
  3.765	241.039
  3.813	235.606
  4.268	342.653
  4.869	537.32
  6.338	1,305.955
  6.405	1,326.044
  6.724	1,617.883
  5.798	1,026.761
  5.292	783.577
  5.099	626.737
  5.356	718.182
  5.505	806.293
  5.033	588.23
  5.001	611.355
  5.135	697.181
  5.482	841.187
  4.86	555.687
  5.409	804.139
  5.891	1,111.429
  4.914	639.026
  4.353	438.612
  5.181	668.182
  6.22	1,281.19
  6.696	1,614.988
  6.228	1,259.686
  5.682	925.413
  5.496	878.548
  6.012	1,107.801
  6.42	1,301.567
  6.545	1,406.517
  5.802	993.675
  6.081	1,179.56
  6.688	1,627.546
  6.731	1,857.072
  6.569	1,583.408
  6.078	1,276.51
  6.487	1,603.953
  7.453	2,346.373
  7.567	2,375.869
  7.329	2,132.565
  7.768	2,632.328
  8.129	3,037.992
  8.032	2,834.459
  9.684	4,119.733
  10.043	4,235.503
  8.946	3,691.27
  9.623	4,133.021
  10.243	4,283.823
  7.788	3,045.937
  9.51	4,061.473
  9.759	4,303.475
  9.758	4,236.179
  9.871	4,212.855
  10.066	4,307.539
  10.618	4,315.448
  10.242	4,213.231
  11.564	4,305.432
  11.049	4,303.939
  10.292	4,313.085
  10.848	4,309.639
  11.289	4,313.872
  11.068	4,314.047
  11.047	4,314.265
  11.89	4,315.439
  11.71	4,314.939
  11.987	4,313.994
  12.916	4,313.323
  12.62	4,305.318
  9.382	3,645.263
  9.528	4,118.464
  8.374	3,430.88
  8.97	3,574.847
  9.515	3,856.118
  10.323	4,122.235
  11.746	4,308.431
  10.089	4,149.978
  10.78	4,246.555
  11.976	4,313.184
  10.725	4,251.287
  10.979	4,292.075
  12.175	4,304.929
  10.654	4,163.349
  11.104	4,283.7
  11.362	4,259.647
  10.881	4,290.584
  11.666	4,309.95
  11.46	4,310.069
  10.362	4,082.999
  9.287	3,834.281
  10.24	4,280.025
  10.823	4,308.479
  10.966	4,306.952
  10.992	4,300.064
  11.161	4,307.827
  11.486	4,308.951
  10.798	4,308.915
  11.362	4,311.007
  11.211	4,287.441
  10.612	4,256.763
  11.071	4,291.614
  10.843	4,272.922
  10.641	4,153.556
  11.188	4,309.133
  11.665	4,296.189
  12.477	4,312.326
  11.132	4,308.156
  11.394	4,312.175
  11.126	4,306.932
  10.344	4,296.546
  11.763	4,308.953
  10.903	4,310.643
  11.624	4,310.497
  12.548	4,310.522
  11.9	4,311.216
  12.086	4,311.471
  11.46	4,309.144
  11.453	4,280.126
  11.749	4,302.071
  10.975	4,307.636
  12.923	4,309.221
  12.653	4,309.138
  10.072	4,247.117
  9.545	4,079.94
  10.511	4,293.168
  9.544	4,127.047
  10.31	4,160.416
  10.751	4,290.321
  10.613	4,279.199
  9.486	4,101.31
  9.521	4,055.313
  9.474	3,831.42
  10.146	4,130.2
  9.819	4,131.445
  10.314	4,177.414
  10.623	4,288.025
  9.732	4,114.744
  10.049	4,210.715
  9.594	3,933.941
  9.738	4,050.571
  10.382	4,104.339
  11.029	4,210.697
  11.536	4,310.186
  11.67	4,311.3
  12.137	4,310.758
  10.767	4,311.403
  9.438	4,238.165
  9.745	4,235.724
  9.531	3,951.222
  9.039	3,623.909
  7.92	2,948.496
  8.826	3,629.7
  9.178	3,848.573
  9.113	3,582.884
  10.748	4,249.804
  9.686	4,178.487
  8.116	3,012.374
  8.369	3,069.508
  8.544	3,114.607
  8.457	3,074.013
  8.334	2,999.203
  7.654	2,461.576
  8.512	2,988.402
  7.989	2,543.555
  8.28	2,717.045
  8.087	2,683.066
  7.938	2,533.606
  9.184	3,661.445
  9.072	3,733.001
  10.577	4,289.763
  9.836	4,133.474
  9.019	3,515.203
  7.399	2,298.459
  6.704	1,817.35
  6.849	1,923.645
  7.095	1,893.373
  7.486	2,047.284
  5.922	1,056.449
  6.869	1,493.234
  6.413	1,339.206
  6.683	1,457.736
  6.542	1,477.655
  7.601	2,143.238
  6.937	1,755.29
  7.149	1,909.143
  7.339	1,924.891
  7.36	2,112.166
  8.161	2,658.512
  7.154	1,922.765
  5.438	886.294
  4.926	688.949
  5.213	686.926
  5.341	738.75
  5.398	709.521
  5.538	814.856
  4.797	592.548
  5.531	866.193
  5.189	791.564
  5.01	595.259
  4.713	525.039
  5.096	722.96
  5.427	840.752
  5.545	926.017
  5.655	907.894
  6.095	1,143.686
  6.548	1,555.587
  5.514	978.642
  4.733	511.7
  3.81	225.671
  3.646	210.344
  3.636	175.315
  4.731	474.81
  4.32	354.681
  4.287	349.616
  3.962	222.556
  3.815	222.676
  3.846	192.6
  4.449	385.64
  4.359	375.023
  3.834	222.516
  3.224	93.099
  2.889	-13.293
  3.461	115.243
  3.004	29.889
  3.781	195.547
  3.724	213.509
  4.589	379.554
  3.63	180.096
  3.059	-11.1
  2.888	-12.978
  3.243	64.5
  3.043	-16.046
  `;
  powerCurveGraphProps.scatter = [];
  realPowerCurveData
    .trim()
    .split("\n")
    .map((line) => {
      const [windSpeed, activePower] = line.trim().split(/\s+/);

      powerCurveGraphProps.scatter.push({
        windSpeed: parseFloat(windSpeed),
        activePower: parseFloat(activePower.replace(/,/g, "")),
      });
    });

  const realDataString = `0:00	6.183	4.31	327.678
  00:10	7.665	5.262	774.395
  00:20	6.73	4.753	471.878
  00:30	7.192	5.138	601.708
  00:40	6.003	3.558	168.27
  00:50	6.017	3.853	212.433
  01:00	6.001	3.866	215.597
  01:10	6.009	3.887	268.879
  01:20	6.008	3.757	213.717
  01:30	6.003	3.142	91.643
  01:40	6.054	4.083	317.043
  01:50	7.132	4.671	587.152
  2:00	6.698	4.567	475.697
  02:10	6.869	4.673	511.309
  02:20	6.641	4.495	450.2
  02:30	7.221	4.504	615.729
  02:40	9.666	6.14	1,545.987
  02:50	7.181	4.919	602.872
  03:00	6.754	4.567	439.218
  03:10	2.8	2.476	-43.355
  03:20	0.136	1.504	-16.294
  03:30	-0.001	2.704	-5.412
  03:40	5.131	4.996	430.25
  03:50	8.528	6.048	1,039.467
  4:00	9.445	6.638	1,433.025
  04:10	9.649	6.717	1,551.676
  04:20	9.147	6.228	1,292.389
  04:30	9.17	6.163	1,307.975
  04:40	9.105	6.105	1,293.551
  04:50	10.032	7.275	1,918.855
  05:00	10.152	7.757	2,137.662
  05:10	9.923	6.729	1,784.658
  05:20	10.082	7.118	1,856.081
  05:30	10.209	7.34	2,063.789
  05:40	10.45	7.593	2,427.994
  05:50	10.121	7.165	1,965.394
  6:00	10.553	8.448	3,083.209
  06:10	10.579	9.236	3,648.87
  06:20	10.583	9.939	4,117.461
  06:30	10.588	10.544	4,307.729
  06:40	10.556	9.508	3,873.698
  06:50	10.594	8.742	3,362.5
  07:00	10.581	9.246	3,711.419
  07:10	10.584	9.123	3,763.133
  07:20	10.578	9.172	3,696.948
  07:30	10.586	9.075	3,465.098
  07:40	10.559	7.83	2,435.111
  07:50	10.577	8.24	3,010.863
  8:00	10.554	8.717	3,200.29
  08:10	10.569	8.054	2,794.738
  08:20	10.592	8.66	3,488.753
  08:30	10.572	9.103	3,687.974
  08:40	10.589	8.744	3,252.98
  08:50	10.557	9.727	3,760.239
  09:00	10.538	10.131	4,097.933
  09:10	10.549	10.39	4,217.159
  09:20	10.585	10.947	4,310.244
  09:30	10.546	9.713	4,028.252
  09:40	10.562	10.489	4,204.418
  09:50	10.558	10.095	4,189.52
  10:00	10.535	9.28	3,940.67
  10:10	10.566	10.126	4,111.92
  10:20	10.568	9.98	4,156.439
  10:30	10.554	10.694	4,276.042
  10:40	10.598	10.82	4,293.292
  10:50	10.59	11.808	4,311.414
  11:00	10.573	10.52	4,279.292
  11:10	10.542	10.676	4,190.356
  11:20	10.586	11.925	4,296.852
  11:30	10.577	11.786	4,298.527
  11:40	10.604	11.608	4,307.375
  11:50	10.601	11.821	4,308.799
  12:00	10.557	11.392	4,196.233
  12:10	10.605	11.803	4,307.57
  12:20	10.604	12.783	4,307.497
  12:30	10.569	12.704	4,306.971
  12:40	10.611	11.798	4,307.213
  12:50	10.592	12.422	4,294.226
  13:00	10.587	12.587	4,309.456
  13:10	10.589	13.334	4,277.174
  13:20	10.593	12.861	4,311.321
  13:30	10.593	13.824	4,308.473
  13:40	10.593	13.651	4,309.602
  13:50	10.593	12.597	4,310.637
  14:00	10.595	13.055	4,312.285
  14:10	10.588	11.502	4,311.548
  14:20	10.604	12.955	4,310.429
  14:30	10.598	14.128	4,314.34
  14:40	10.587	13.482	4,306.399
  14:50	10.602	14.969	4,311.285
  15:00	10.606	15.679	4,313.4
  15:10	10.601	14.818	4,310.9
  15:20	10.588	14.487	4,312.559
  15:30	10.599	14.406	4,311.511
  15:40	10.607	15.432	4,313.716
  15:50	10.594	14.504	4,306.855
  16:00	10.558	14.213	4,309.101
  16:10	10.598	14.24	4,309.037
  16:20	10.595	15.143	4,309.944
  16:30	10.601	14.48	4,311.819
  16:40	10.596	14.963	4,312.014
  16:50	10.595	13.841	4,309.536
  17:00	10.579	13.687	4,293.317
  17:10	10.525	11.405	4,221.658
  17:20	10.595	12.296	4,294.5
  17:30	10.533	10.914	4,221.332
  17:40	10.573	12.688	4,288.424
  17:50	10.595	12.752	4,310.908
  18:00	10.596	12.809	4,310.626
  18:10	10.602	11.086	4,311.037
  18:20	10.602	10.773	4,307.577
  18:30	10.583	10.778	4,302.389
  18:40	10.556	9.887	4,222.115
  18:50	10.56	10.074	4,133.337
  19:00	10.578	10.929	4,254.938
  19:10	10.533	10.849	4,070.624
  19:20	10.577	11.637	4,282.635
  19:30	10.563	10.476	4,238.158
  19:40	10.565	10.329	4,183.779
  19:50	10.575	11.352	4,297.41
  20:00	10.585	11.529	4,298.507
  20:10	10.594	11.811	4,286.199
  20:20	10.588	12.438	4,306.859
  20:30	10.594	11.954	4,308.566
  20:40	10.577	12.104	4,291.37
  20:50	10.607	12.903	4,311.354
  21:00	10.584	12.268	4,310.19
  21:10	10.607	12.988	4,311.735
  21:20	10.59	12.887	4,312.841
  21:30	10.6	13.032	4,309.869
  21:40	10.591	11.672	4,310.133
  21:50	10.588	10.96	4,312.276
  22:00	10.608	10.996	4,309.089
  22:10	10.546	10.605	4,207.945
  22:20	10.542	9.489	3,978.345
  22:30	10.576	8.837	3,534.434
  22:40	10.603	8.347	3,005.849
  22:50	10.58	9.117	3,628.44
  23:00	10.557	10.091	4,072.129
  23:10	10.556	9.662	4,040.174
  23:20	10.555	9.408	3,909.637
  23:30	10.569	8.938	3,689.256
  23:40	10.343	7.773	2,368.905
  23:50	10.226	7.462	2,337.817
  `;
  const dailyRWPGraphProps = {} as DailyRWPGraphProps;
  dailyRWPGraphProps.dailyRWPGraphProps = [];

  realDataString
    .trim()
    .split("\n")
    .map((line) => {
      const [time, rotorSpeed, windSpeed, activePower] = line
        .trim()
        .split(/\s+/);

      dailyRWPGraphProps.dailyRWPGraphProps.push({
        time: time,
        rotorSpeed: parseFloat(rotorSpeed),
        windSpeed: parseFloat(windSpeed),
        activePower: parseFloat(activePower.replace(/,/g, "")),
      });
    });

  const [EventTextBox, setEventTextBox] = useState([
    {
      title: "1. 주요 이벤트 현황",
      content:
        "1. [20011] HYD. Rotor brake storage pressure low 알람 PCS 점검 (백규열 주임) - 21:56 ~ 22:01",
    },
    {
      title: "2. 주요 에러 발생현황 및 조치사항",
    },
    {
      title: "3. 현장이슈 발생현황",
    },
  ]);
  return (
    <Preview
      powerCurveGraphProps={powerCurveGraphProps}
      dailyRWPGraphProps={dailyRWPGraphProps}
      eventTextBoxProps={EventTextBox}
      setEventTextBoxProps={setEventTextBox}
    />
  );
};

export default PreviewContainer;
