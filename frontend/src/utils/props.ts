export const isPropsEmpty = (props: unknown) => {
  if (!props || Object.keys(props).length === 0) {
    return true;
  }
};

export const arePropsEmpty = (...propsList: unknown[]) => {
  return propsList.every((props) => !props || Object.keys(props).length === 0);
};
